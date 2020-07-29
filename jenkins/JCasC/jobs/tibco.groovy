job('Tibco') {

  steps {
    shell (
      'cd /usr/share/jenkins/synapse/'
      'ls'
      )
  }
}
