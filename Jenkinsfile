pipeline{

    agent any
    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {
            steps{
               sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
            }
        }

        stage('Unit Tests') {
            steps{
               sh 'mvn test -Dmaven.test.skip=false'
            }
        }

        stage('Integration Tests') {
            steps{
               sh 'mvn -Dtests.unit.skip=true verify'
            }
        }

        stage('Sonar Analysis') {
            environment {
                scannerHome = tool 'SONAR_SCANNER'
            }
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=api-commerce -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_fbf5cbdf88860b5ae3818d3ba15f18f12d85fa96'
                }
            }
        }

        stage ('Quality Gate') {
            steps {
                sleep(5)
                timeout(time: 1, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }
    }

    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, target/failsafe-reports/*.xml'            
        }        
    }
}