def TAG_SELECTOR = "UNINTIALIZED"

pipeline{

    agent any

    options {
        ansiColor('xterm')
    }

    stages{

        stage('Build') {
            steps{
               echo("Execute Build")
               sh 'mvn -Dmaven.test.skip=true -Dtests.unit.skip=true clean package'
               script {
                    TAG_SELECTOR = readMavenPom().getVersion()
                }
                echo("Build done")              
            }
        }

        stage('Unit Tests') {
            steps{
               echo("Execute unit tests")
               sh 'mvn test -Dmaven.test.skip=false'
            }
            echo("Unit tests done")
        }

        stage('Integration Tests') {
            steps{
               echo("Execute integration tests")
               sh 'mvn -Dtests.unit.skip=true verify'
            }
            echo("Integration tests done")
        }

        stage('Sonar Analysis') {        
            steps{
                withSonarQubeEnv('SONAR_LOCAL') {
                    echo("Execute sonar static code")
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
                echo("Sonar static code success") 
            }
        }

         stage('Analyze dockerfile'){
            steps{
                script {
                    try {
                        echo("Execute analyze dockerfile")                        
                        sh 'docker run --rm -i hadolint/hadolint < Dockerfile'
                    } catch (Exception e) {
                        sh "echo $e"
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                    echo("Analyze dockerfile done")
                }
            }
        }
        
        stage('Build image'){
            steps{
                script {
                    try {
                        echo("Execute build image")                        
                        sh "docker build -t ilimafilho/commerce:${TAG_SELECTOR} ."
                    } catch (Exception e) {
                        sh "echo $e"              
                        currentBuild.result = 'ABORTED'
                        error('Erro')
                    }
                    echo("Build image done")  
                }
            }
        }
        
        stage('Push image'){
            steps{
                script{
                    try {
                        echo("Execute push image")  
                        withDockerRegistry([ credentialsId: "dockerhub", url: "" ]) {
                        sh "docker push ilimafilho/commerce:${TAG_SELECTOR}"
                        echo("Remove image local") 
                        sh "docker image rm ilimafilho/commerce:${TAG_SELECTOR}"
                    }
                    } catch (Exception e) {
                        sh "echo $e"
                    }
                    echo("Push image done")  
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