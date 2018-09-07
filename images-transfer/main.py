import os
import docker
import requests
from requests.auth import HTTPBasicAuth

from_repo_url = os.environ['FROM_REPO_URL']
from_repo_user = os.environ['FROM_REPO_USER']
from_repo_pwd = os.environ['FROM_REPO_PWD']
from_repo_project_id = os.environ['FROM_REPO_PROJECT_ID']
to_repo = os.environ['TO_REPO']
to_repo_org = os.getenv('TO_REPO_ORG', '')

docker_url = os.getenv('DOCKER_SOCKET', 'unix://var/run/docker.sock')
client = docker.APIClient(base_url=docker_url, version='auto')


def main():
    print(docker_url)
    from_repo = from_repo_url.replace('https://', '').replace('http://', '')
    for repo_info in requests.get('{}/api/repositories'.format(from_repo_url),
                                  params={"project_id": 2}, auth=HTTPBasicAuth(from_repo_user, from_repo_pwd)).json():
        for tag_info in requests.get('{}/api/repositories/{}/tags'.format(from_repo_url, repo_info['name']),
                                     auth=HTTPBasicAuth(from_repo_user, from_repo_pwd)).json():
            identity = '{}:{}'.format(repo_info['name'], tag_info['name'])
            client.pull('{}/{}'.format(from_repo, identity))
            if to_repo_org:
                tmp = repo_info['name'].split('/')
                to_repo_identity = '{}/{}:{}'.format(to_repo_org, tmp[len(tmp) - 1], tag_info['name'])
            else:
                to_repo_identity = identity
            client.tag('{}/{}'.format(from_repo, identity), to_repo_identity)
            print(client.push('{}/{}'.format(to_repo, to_repo_identity)))


if __name__ == '__main__':
    main()
