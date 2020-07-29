job('Tibco Delete') {

  steps {
    shell (
      '''
cd /usr/share/jenkins/synapse/
export GOOGLE_APPLICATION_CREDENTIALS=credentials.json
ls
make destroy
      '''
      )
  }
}
