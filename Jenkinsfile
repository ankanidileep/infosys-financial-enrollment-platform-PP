pipeline {
  agent any

  tools {
    maven 'mymaven'
    jdk 'jdk21'
  }

  environment {
    AWS_REGION = 'us-east-1'
    ECR_REGISTRY = credentials('ecr-registry')
    IMAGE_TAG = "${BUILD_NUMBER}-${GIT_COMMIT.take(7)}"
  }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Unit Test & Package') {
      steps {
        sh '''
          set -e
          for d in services/*; do
            (cd "$d" && mvn -B clean test package)
          done
        '''
      }
      post {
        always {
          junit allowEmptyResults: true, testResults: 'services/**/target/surefire-reports/*.xml'
        }
      }
    }

    stage('SonarQube') {
      steps {
        // Configure a SonarQube server named "sonarqube" in Jenkins.
        withSonarQubeEnv('sonarqube') {
          sh 'mvn -B -f services/account-ingestion-service/pom.xml sonar:sonar -Dsonar.projectKey=financial-platform'
        }
      }
    }

    stage('OWASP Dependency Check') {
      steps {
        // Install/configure the Dependency-Check Jenkins plugin or CLI in the agent.
        sh 'echo "Run dependency-check against all service pom.xml files here"'
      }
    }

    stage('Docker Build') {
      steps {
        sh '''
          set -e
          for d in services/*; do
            name=$(basename "$d")
            docker build -t "$ECR_REGISTRY/$name:$IMAGE_TAG" "$d"
          done
        '''
      }
    }

    stage('Trivy Scan') {
      steps {
        sh '''
          set -e
          for d in services/*; do
            name=$(basename "$d")
            trivy image --exit-code 1 --severity HIGH,CRITICAL "$ECR_REGISTRY/$name:$IMAGE_TAG"
          done
        '''
      }
    }

    stage('Push ECR') {
      steps {
        sh '''
          set -e
          aws ecr get-login-password --region "$AWS_REGION" |
            docker login --username AWS --password-stdin "$ECR_REGISTRY"
          for d in services/*; do
            name=$(basename "$d")
            docker push "$ECR_REGISTRY/$name:$IMAGE_TAG"
          done
        '''
      }
    }

    stage('GitOps Update') {
      steps {
        sh '''
          echo "In production, update the GitOps repository with IMAGE_TAG and let Argo CD reconcile."
        '''
      }
    }
  }
}
