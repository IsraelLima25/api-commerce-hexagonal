def NAME_ARTIFACT = "UNINTIALIZED"
def VERSION_ARTIFACT = "UNINTIALIZED"

pipeline{

    agent any

    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {            
            steps{               
               sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
               script {
                    NAME_ARTIFACT = readMavenPom().getName()
                    VERSION_ARTIFACT = readMavenPom().getVersion()                     
                }                        
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
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {                    
                    sh 'mvn clean package sonar:sonar -Dsonar.projectKey=api-commerce -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_26c7e663f42ab1590d739142a7dcd638a9df423f'
                }
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        sleep(10)
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to a quality gate failure: ${qg.status}"
                        }
                    }
                }                
            }
        }

         stage('Analyze dockerfile'){
            steps{
                script {
                    try {                                      
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
                        sh "docker build --build-arg JAR_FILE=${NAME_ARTIFACT}-${VERSION_ARTIFACT}.jar -t ilimafilho/${NAME_ARTIFACT}:${VERSION_ARTIFACT} ."
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
                        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
                        sh "docker push ilimafilho/${NAME_ARTIFACT}:${VERSION_ARTIFACT}"                        
                        sh "docker image rm ilimafilho/${NAME_ARTIFACT}:${VERSION_ARTIFACT}"
                    }
                    } catch (Exception e) {
                        sh "echo $e"
                    }                    
                }
            }
        }

        stage('Deploy'){
            steps{
                sh 'docker compose up -d'
            }
        }  
    }
    
    post {
        always {
            junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml, target/failsafe-reports/*.xml'            
        }        
    }
}