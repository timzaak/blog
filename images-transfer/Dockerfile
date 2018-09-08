FROM python:3.7-alpine3.8
COPY main.py /main.py
COPY requirements.txt /requirements.txt
RUN pip install -r requirements.txt
ENTRYPOINT ["python", "/main.py"]