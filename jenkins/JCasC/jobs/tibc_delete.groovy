job('Tibco Delete') {

  steps {
    shell (
      '''
cd /usr/share/jenkins/synapse/
export GOOGLE_APPLICATION_CREDENTIALS=credentials.json
ls
terraform destroy -var-file="/vars/dev.tfvar" -auto-approve
      '''
      )
  }
}
