-- from http://blog.csdn.net/yueguanghaidao/article/details/52862066

local json = require "cjson"
local http = require "resty.http"

-- consul watcher
-- 设置key
-- curl -X PUT http://172.20.20.10:8500/v1/kv/broker/kafka/172.20.20.11:8080
--
-- 获取所有前缀key
-- curl http://172.20.20.10:8500/v1/kv/broker/kafka/?recurse
-- [{"LockIndex":0,"Key":"broker/kafka/172.20.20.11:8080","Flags":0,"Value":null,"CreateIndex":34610,"ModifyIndex":34610}]
--
-- 获取所有key,index大于34610版本号(当有多个key时需要获取最大版本号)
-- 没有更新,consul阻塞5分钟
-- curl "http://172.20.20.10:8500/v1/kv/broker/kafka/?recurse&index=34610"

local cache = {}
setmetatable(cache, { __mode="kv"} )

local DEFAULT_TIMEOUT = 6 * 60 * 1000 -- consul默认超时5分钟

local _M = {}
local mt = { __index = _M }


function _M.new(self, watch_url, callback)
    local watch = cache[watch_url]
    if watch ~= nil then
        return watch
    end

    local httpc, err = http.new()
    if not httpc then
        return nil, err
    end
    httpc:set_timeout(DEFAULT_TIMEOUT)

    local recurse_url = watch_url .. "?recurse"
    watch =  setmetatable({
        httpc = httpc,
        recurse_url = recurse_url,
        modify_index = 0,
        running = false,
        stop = false,
        callback = callback,
    }, mt)

    cache[watch_url] = watch
    return watch
end



function _M.start(self)
    if self.running then
        ngx.log(ngx.ERR, "watch already start, url:", self.recurse_url)
        return
    end
    local is_exiting = ngx.worker.exiting
    local watch_index= function()
        repeat
            local prev_index = self.modify_index
            local wait_url = self.recurse_url .. "&index=" .. prev_index
            ngx.log(ngx.ERR, "wait:", wait_url)
            local result = self:request(wait_url)
            if result then
                self:get_modify_index(result)
                if self.modify_index > prev_index then -- modify_index change
                    ngx.log(ngx.ERR, "watch,url:", self.recurse_url, " index change")
                    self:callback(result)
                end
            end

        until self.stop or is_exiting()
        ngx.log(ngx.ERR, "watch exit, url: ", self.recurse_url)
    end

    local ok, err = ngx.timer.at(1, watch_index)
    if not ok then
        ngx.log(ngx.ERR, "failed to create watch timer: ", err)
        return
    end
    self.running = true
end


function _M.stop(self)
    self.stop = true
    ngx.log(ngx.ERR, "watch stop, url:", self.recurse_url)
end


function _M.get_modify_index(self, result)
    local key = "ModifyIndex"
    local max_index = self.modify_index
    for _, v in ipairs(result) do
        local index = v[key]
        if index > max_index then
            max_index = index
        end
    end
    self.modify_index = max_index
end


function _M.request(self, url)
    local res, err = self.httpc:request_uri(url)
    if not res then
        ngx.log(ngx.ERR, "watch request error, url:", url, " error:", err)
        return nil, err
    end
    return json.decode(res.body)
end

return _M


