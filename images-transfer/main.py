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
to_repo_pwd = os.getenv('TO_REPO_PWD', '')
to_repo_user = os.getenv('TO_REPO_USER', '')
to_repo_email = os.getenv('TO_REPO_EMAIL', '')

docker_url = os.getenv('DOCKER_SOCKET', 'unix://var/run/docker.sock')
client = docker.APIClient(base_url=docker_url, version='auto')


def main():
    if to_repo_pwd:
        print(client.login(username=to_repo_user, password=to_repo_pwd, email=to_repo_email, registry=to_repo))
    from_repo = from_repo_url.replace('https://', '').replace('http://', '')
    print(client.login(username=from_repo_user, password=from_repo_pwd, registry=from_repo))
    for repo_info in requests.get('{}/api/repositories'.format(from_repo_url),
                                  params={"project_id": from_repo_project_id},
                                  auth=HTTPBasicAuth(from_repo_user, from_repo_pwd)).json():
        for tag_info in requests.get('{}/api/repositories/{}/tags'.format(from_repo_url, repo_info['name']),
                                     auth=HTTPBasicAuth(from_repo_user, from_repo_pwd)).json():
            client.pull('{}/{}'.format(from_repo, repo_info['name']), tag_info['name'])
            if to_repo_org:
                tmp = repo_info['name'].split('/')
                to_repo_identity = '{}/{}'.format(to_repo_org, tmp[len(tmp) - 1], )
            else:
                to_repo_identity = repo_info['name']
            print(client.tag('{}/{}:{}'.format(from_repo, repo_info['name'], tag_info['name']),
                             '{}/{}'.format(to_repo, to_repo_identity), tag_info['name']))
            print('push {}/{}:{}',to_repo, to_repo_identity ,tag_info['name'])
            print(client.push('{}/{}'.format(to_repo, to_repo_identity), tag_info['name']))


if __name__ == '__main__':
    main()
