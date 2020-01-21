# Activiti-7
* Activiti Version : `7.1.0.M5`

# Activiti Cloud
Pre-requisites: 
* Docker for Desktop.
* Enable Kubernetes in Docker -> Preferences -> Kubernetes [Also Enable `Deploy Docker Stacks to Kubernetes by Default`]
* Helm : `2` [`brew install helm@2`]

### Add Helm Repo
* `helm repo add activiti-cloud-charts https://activiti.github.io/activiti-cloud-charts/`
* `helm repo add sample-helm-repo https://mrunal-badhe.github.io/helm-repo/`

### Kubernetes dashboard
* Install Kubernetes dashboard
```
helm install stable/kubernetes-dashboard --name kubernetes-dashboard --set=rbac.create=false,enableSkipLogin=true,enableInsecureLogin=true
```
* Create Kubernetes namespace
```
kubectl create namespace activiti7
```
* Enable Port Forwarding
```
export POD_NAME=$(kubectl get pods -n default -l "app=kubernetes-dashboard,release=kubernetes-dashboard" -o jsonpath="{.items[0].metadata.name}")

kubectl -n default port-forward $POD_NAME 9090:9090
```

### Install Infra
* Install nginx
```
helm install stable/nginx-ingress --namespace=activiti7
```
* Check the Kubernetes Pods status
```
kubectl get services --namespace=activiti7
```

### Configure the Full Example

* Clone activiti cloud charts on your local.
```
git clone https://github.com/Activiti/activiti-cloud-charts
```

* Get your IP
```
hostname
ping <HOSTNAME_RESULT>.local
ping <IP>.nip.io
```
* Update your IP in values.yaml

Open up the activiti-cloud-charts/activiti-cloud-full-example/values.yaml file and 
replace the string `REPLACEME` with <YOUR IP>.nip.io

### Deploy the Full Example
* Execute below commands to deploy Full Example

```
helm repo update
Go to directory:  activiti_cloud_charts[The folder where you cloned activiti cloud charts]
cd activiti_cloud_charts/activiti-cloud-full-example
helm install -f values.yaml activiti-cloud-charts/activiti-cloud-full-example --namespace=activiti7
```

* Now, wait for all the services to be up and running, check the pods as follows:
```
kubectl get pods --namespace=activiti7
```

* If you see an insufficient memory error then you need to increase the available memory for 
Kubernetes running in Docker for Desktop (Preferences | Advanced | Memory). 
You will also see a lot of these readiness probe failed errors. 
They will eventually disappear, but it will take 5-10 minutes.

### Quick Look : Activiti Modeler

* http://activiti-cloud-gateway.activiti7.<YOUR_IP>>.nip.io/activiti-cloud-modeling  URL.
* You will get a SSO button, click it and then you will get the login screen.
* Make sure you login with  `modeler/password` as this user is part of the ACTIVITI_MODELER role. 

### <a name="collectionImport"></a>Interacting with the Full Example Deployment using Postman

* Clone the following project:  
  `$ git clone https://github.com/Activiti/activiti-cloud-examples.git`

  In Postman import the collection as follows. Select File | Import… so you see:
* Click Choose Files and then navigate and pick the following file (i.e. Activiti v7 REST API.postman_collection.json) from the project we just cloned:
* Before calling any service you will need to create a new Environment in Postman. You can do that by going to the Manage Environment icon (cogwheel in upper right corner):

    ```
    In Manage Environments click the Add button to add a new environment. Give it a name such as Activiti 7. Then configure the following variables for the environment: 
    gateway = activiti-cloud-gateway.activiti7.<YOUR_IP>.nip.io
    idm =activiti-cloud-gateway.activiti7.<YOUR_IP>.nip.io
    realm = activiti:
    ```
  
## Deploying Sample Runtime Bundle and Sample Cloud Connector

* Clone this repo on your local.
* Go to Folder: `cd sample-activiti7-runtime-bundle`
* Build the project, it would create docker image. `mvn clean install -DskipTests` OR you can simply build your docker image using `docker build`
* Go to Folder: `cd sample-activiti7-cloud-connector`
* Build the project, it would create docker image. `mvn clean install -DskipTests` OR you can simply build your docker image using `docker build`
* Go to folder where activiti_cloud_charts repo is checked out.
* cd to folder activiti-cloud-full-example
* Update `requirements.yaml` file to add below entries
    ```
    - name: sample-runtime-bundle
      repository: https://mrunal-badhe.github.io/helm-repo/
      version: 0.0.1
    - name: sample-cloud-connector
      repository: https://mrunal-badhe.github.io/helm-repo/
      version: 0.0.1
    ```
* Update Dependencies using command: `helm dependency update`
* Upgrade the existing activiti full example using below command:
    ```
    helm upgrade <YOUR APP NAME> -f values.yaml . --namespace=activiti7
    ```
  The app name can be checked using command `helm list`
* Import Sample Runtime Bundle Postman Collection using the steps mentioned in [Interacting with the Full Example Deployment using Postman](#collectionImport)

### Basic Flow you can test

* Get the process definitions using token for `hradmin`
* start process using `processKey`
* Get tasks using token for `testuser`
* Complete task using task ID received in previous step.

# Activiti Core
Please refer: [acitiviti7-api-basic-events-README.md](activiti7-api-basic-events/README.md)