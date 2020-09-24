# Jenkins master manifest
Below are examples of the standard Jenkins master manifest and a manifest which contains a deployment pattern that mounts secrets.

### Standard Jenkins master deployment

```
apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: jenkins-master
  name: jenkins-master
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins-master
  strategy: {}
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: jenkins-master
    spec:
      imagePullSecrets:
        - name: "docker-hub"
      spec:
      containers:
       - name: jenkins-master
         image: gcr.io/tranquility-base-images/tb-jenkins_latest:landingzone
         securityContext:
            privileged: true
            runAsUser: 0
         imagePullPolicy: "Always"
         volumeMounts:
          - mountPath: /var
            name: jenkins-home
            subPath: jenkins_home
          - name: docker-sock-volume
            mountPath: /var/run/docker.sock
         resources:
           requests:
             memory: "1024Mi"
             cpu: "0.5"
           limits:
             memory: "1024Mi"
             cpu: "0.5"
         ports:
           - name: http-port
             containerPort: 8080
           - name: jnlp-port
             containerPort: 50000
      volumes:
       - name: jenkins-home
         persistentVolumeClaim:
          claimName: jenkins-master-pv-claim
       - name: docker-sock-volume
         hostPath:
           path: /var/run/docker.sock
           type: File
status: {}
```

### Standard Jenkins master deployment with mounted secrets example

```
apiVersion: apps/v1
kind: Deployment
metadata:
  creationTimestamp: null
  labels:
    app: jenkins-master
  name: jenkins-master
spec:
  replicas: 1
  selector:
    matchLabels:
      app: jenkins-master
  strategy: {}
  template:
    metadata:
      creationTimestamp: null
      labels:
        app: jenkins-master
    spec:
      imagePullSecrets:
        - name: "docker-hub"
      spec:
      containers:
       - name: jenkins-master
         image: gcr.io/tranquility-base-images/tb-jenkins_latest:landingzone
         securityContext:
            privileged: true
            runAsUser: 0
         imagePullPolicy: "Always"
         volumeMounts:
          - mountPath: /var
            name: jenkins-home
            subPath: jenkins_home
          - name: docker-sock-volume
            mountPath: /var/run/docker.sock
          - name: google-cloud-key
            mountPath: /var/secrets/google
          env:
            - name: GOOGLE_APPLICATION_CREDENTIALS
              value: /var/secrets/google/ec-service-account-config.json
         resources:
           requests:
             memory: "1024Mi"
             cpu: "0.5"
           limits:
             memory: "1024Mi"
             cpu: "0.5"
         ports:
           - name: http-port
             containerPort: 8080
           - name: jnlp-port
             containerPort: 50000
      volumes:
       - name: config-volume
          configMap:
            name: ec-config
       - name: google-cloud-key
          secret:
            secretName: ec-service-account
       - name: jenkins-home
         persistentVolumeClaim:
          claimName: jenkins-master-pv-claim
       - name: docker-sock-volume
         hostPath:
           path: /var/run/docker.sock
           type: File
status: {}

```

Files defining storage and the like are shown below.

pvc.yaml

```
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: jenkins-master-pv-claim
  namespace: jenkins
spec:
  accessModes:
  - ReadWriteOnce
  resources:
    requests:
      storage: 50Gi
  storageClassName: gold

```
storage-class.yaml

```
kind: StorageClass
apiVersion: storage.k8s.io/v1
metadata:
  name: gold
provisioner: kubernetes.io/gce-pd
parameters:
  type: pd-ssd

```
