pipeline{

    agent any
    options {
        ansiColor('xterm')
    }

    stages{

        // stage('Build') {
        //     steps{
        //        sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
        //     }
        // }

        // stage('Unit Tests') {
        //     steps{
        //        sh 'mvn test -Dmaven.test.skip=false'
        //     }
        // }

        // stage('Integration Tests') {
        //     steps{
        //        sh 'mvn -Dtests.unit.skip=true verify'
        //     }
        // }

        // stage('Sonar Analysis') {            
        //     steps{
        //         withSonarQubeEnv('SONAR_LOCAL') {
        //             sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=api-commerce -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_26c7e663f42ab1590d739142a7dcd638a9df423f'
        //         }
        //         timeout(time: 5, unit: 'MINUTES') {
        //             script {
        //                 def qg = waitForQualityGate()
        //                 if (qg.status != 'OK') {
        //                     error "Pipeline aborted due to a quality gate failure: ${qg.status}"
        //                 }
        //             }
        //         } 
        //     }
        // }

         stage('Analyze dockerfile'){
            steps{
                script {
                    try {
                        sh 'echo Analisando dockerfile'
                        sh 'docker run --rm -i hadolint/hadolint < Dockerfile'
                    } catch (Exception e) {
                        sh "echo $e"
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                }
            }
        }
        
        stage('Build image'){
            steps{
                script {
                    try {
                        sh 'echo Construindo imagem'
                        sh 'docker build -t ilimafilho/commerce:1.0.0 .'
                    } catch (Exception e) {
                        sh "echo $e"              
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                }
            }
        }
        
        stage('Push image'){
            steps{
                script{
                    try {
                        sh 'echo Enviando imagem para repositorio remoto'
                        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
                        sh 'docker push ilimafilho/commerce:1.0.0'
                        sh 'echo Removendo cache imagem local'
                        sh 'docker image rm ilimafilho/commerce'
                    }
                    } catch (Exception e) {
                        sh "echo $e"
                    }
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