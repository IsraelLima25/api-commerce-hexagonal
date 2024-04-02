pipeline{

    agent any

    stages{

        stage('Clone repository') {
            steps{
               git branch: 'main', 
               credentialsId: 'github', 
               url: 'git@github.com:IsraelLima25/api-commerce-hexagonal.git'
            }
        }
    }
}