job('Tibco Delete') {

  steps {
    shell (
      '''
cd /usr/share/jenkins/synapse/
ls
terraform destroy -var-file="/vars/dev.tfvars" -auto-approve
      '''
      )
  }
}
