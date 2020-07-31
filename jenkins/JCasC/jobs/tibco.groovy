job('Tibco') {

  steps {
    shell (
      '''
cd /usr/share/jenkins/synapse/
ls
make init
make plan
make apply
      '''
      )
  }
}
