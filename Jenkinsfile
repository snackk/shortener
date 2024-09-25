#!/usr/bin/env groovy

node {

    stage('Checkout') {
        checkout scm
    }

    stage('Docker Creds Setup') {
        withCredentials([usernamePassword(credentialsId: 'snackk_docker', passwordVariable: 'dockerhub_pwd', usernameVariable: 'dockerhub_usr')])
            {
                sh "echo ${dockerhub_pwd} | docker login -u ${dockerhub_usr} --password-stdin"
            }
    }

    stage('Check java') {
        sh "java -version"
    }

    stage('Clean') {
        sh "chmod +x mvnw"
        sh "./mvnw clean"
    }

    stage('Tests') {
        try {
            sh "./mvnw test"
        } catch(err) {
            throw err
        } finally {
            junit '**/target/surefire-reports/TEST-*.xml,**/target/failsafe-reports/TEST-*.xml'
        }
    }

    stage('Packaging') {
        sh "./mvnw package"
        archiveArtifacts artifacts: '**/target/*.jar', fingerprint: true
    }

    def dockerImage
    stage('Publish Docker') {
        // A pre-requisite to this step is to setup authentication to the docker registry
        // https://github.com/GoogleContainerTools/jib/tree/master/jib-maven-plugin#authentication-methods
        sh "./mvnw jib:build"
    }
}
