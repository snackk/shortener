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

        stage('Increment and Create Tag') {
            steps {
                script {
                    sh "git fetch --tags"
                    def latestTag = sh(script: "git describe --tags `git rev-list --tags --max-count=1`", returnStdout: true).trim()
                    def (major, minor, patch) = latestTag.tokenize('.')
                    patch = patch.toInteger() + 1
                    env.NEW_TAG = "${major}.${minor}.${patch}"
                    echo "New tag will be: ${env.NEW_TAG}"
                    withCredentials([usernamePassword(credentialsId: 'github', passwordVariable: 'GIT_TOKEN', usernameVariable: 'GIT_USER')]) {
                        sh "git config user.email '${GIT_USER}'"
                        sh "git config user.name 'Diogo Santos'"

                        def scmUrl = scm.getUserRemoteConfigs()[0].getUrl()
                        def repoPath = scmUrl.replaceFirst(/^https:\/\/github.com\//, '').replaceFirst(/\.git$/, '')
                        def authenticatedUrl = "https://${GIT_TOKEN}@github.com/${repoPath}.git"
                        sh "git tag ${env.NEW_TAG}"
                        sh "git push ${authenticatedUrl} ${env.NEW_TAG}"
                    }
                }
            }
        }

        stage('Package') {
            steps {
                script {
                    def parsedVersion = env.NEW_TAG.replaceFirst('^v', '')
                    sh "./mvnw versions:set -DnewVersion=${parsedVersion}"
                    sh "./mvnw package"
                    archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
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
