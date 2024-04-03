def TAG_SELECTOR = "UNINTIALIZED"

pipeline{

    agent any

    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {
            steps{
               sh 'echo Execute Build'
               sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
               script {
                    TAG_SELECTOR = readMavenPom().getVersion()
                }
              sh 'echo Build done'           
            }
        }

        stage('Unit Tests') {
            steps{
               sh 'echo Execute unit tests'
               sh 'mvn test -Dmaven.test.skip=false'
            }
            sh 'echo Unit tests done'
        }

        stage('Integration Tests') {
            steps{
               sh 'echo Execute integration tests'
               sh 'mvn -Dtests.unit.skip=true verify'
            }
            sh 'echo Integration tests done'
        }

        stage('Sonar Analysis') {        
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {
                    sh 'echo Execute sonar static code'
                    sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=api-commerce -Dsonar.host.url=http://localhost:9000 -Dsonar.login=sqp_26c7e663f42ab1590d739142a7dcd638a9df423f'
                }
                timeout(time: 5, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to a quality gate failure: ${qg.status}"
                        }
                    }
                }
                sh 'echo Sonar static code success' 
            }
        }

         stage('Analyze dockerfile'){
            steps{
                script {
                    try {
                        sh 'echo Execute analyze dockerfile'                    
                        sh 'docker run --rm -i hadolint/hadolint < Dockerfile'
                    } catch (Exception e) {
                        sh "echo $e"
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                    sh 'echo Analyze dockerfile done'
                }
            }
        }
        
        stage('Build image'){
            steps{
                script {
                    try {
                        sh 'echo Execute build image'                        
                        sh "docker build -t ilimafilho/commerce:${TAG_SELECTOR} ."
                    } catch (Exception e) {
                        sh "echo $e"              
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                    sh 'echo Build image done'  
                }
            }
        }
        
        stage('Push image'){
            steps{
                script{
                    try {
                        sh 'echo Execute push image'  
                        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
                        sh "docker push ilimafilho/commerce:${TAG_SELECTOR}"
                        sh 'echo Remove image local' 
                        sh "docker image rm ilimafilho/commerce:${TAG_SELECTOR}"
                    }
                    } catch (Exception e) {
                        sh "echo $e"
                    }
                    sh 'echo Push image done'  
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