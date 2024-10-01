pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS = credentials('snackk_docker')
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Setup') {
            steps {
                sh "chmod +x mvnw"
                sh "java -version"
            }
        }

        stage('Build and Test') {
            steps {
                sh "./mvnw clean test"
            }
            post {
                always {
                    junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh "./mvnw package"
                archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
            }
        }

        stage('Increment and Create Tag') {
            when {
                branch 'main'
            }
            steps {
                script {
                    sh "git fetch --tags"
                    def latestTag = sh(script: "git describe --tags `git rev-list --tags --max-count=1`", returnStdout: true).trim()
                    def (major, minor, patch) = latestTag.tokenize('.')
                    patch = patch.toInteger() + 1
                    env.NEW_TAG = "${major}.${minor}.${patch}"
                    echo "New tag will be: ${env.NEW_TAG}"
                    withCredentials([usernamePassword(credentialsId: 'github', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                        sh "git config user.email '${GIT_USERNAME}'"
                        sh "git config user.name 'snackk'"
                        sh "git tag -a v${env.NEW_TAG} -m 'Version ${env.NEW_TAG}'"
                        def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()
                        def repoUrl = scmUrl.replaceFirst(/(https?:\/\/)/, "\$1${GIT_USERNAME}:${GIT_PASSWORD}@")

                        sh "git push ${repoUrl} v${env.NEW_TAG}"
                    }
                }
            }
        }

        stage('Publish Docker') {
            steps {
                script {
                    docker.withRegistry('', 'snackk_docker') {
                        sh "./mvnw jib:build"
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}
