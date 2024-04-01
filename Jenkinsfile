pipeline{

    agent any

    stages{

        stage('Clone repository') {
            steps{
                git url: 'git@github.com:IsraelLima25/api-commerce-hexagonal.git',
                credentialsId: 'github',
                branch: 'main'
            }
        }
    }
}