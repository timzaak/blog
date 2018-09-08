### Docker images Transfer
Transfer images from a harbor repo to another repo


the input is
```python
import os
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

```