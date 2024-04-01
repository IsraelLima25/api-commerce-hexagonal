pipeline{

    agent any

    stages{

        stage('Clone repository') {
            steps{
               git branch: 'main', 
               credentialsId: 'github', 
               url: 'https://github.com/IsraelLima25/zeca'
            }
        }
    }
}