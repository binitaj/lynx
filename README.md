# Secrets Management for Small IT Businesses
## Problem Statement:
Software startups and small IT businesses often face unique challenges in securely managing confidential data, such as, Personally Identifiable Information (PII), credentials, and other sensitive information. Many companies struggle to keep their sensitive data secure and often face data breaches. 
The problem of data breaches is rampant in the industry—not just affecting small IT companies, but also the giants. Many of these incidents stemmed from a lack of rigor in storing and managing secrets and sensitive information. The problem continues to become severe due to the ever-growing regulatory requirements. SOC 2 Type 2 for storing sensitive customer information, HIPAA for patient information, GDPR and CCPA for PII, COPPA for children’s online activities are all regulatory requirements around data protection.  
## Compliance Requirements 
For this project, we initially wanted to focus on Soc 2 Type 2 compliance, which specifies the guidelines to businesses handling sensitive customer data. Small IT companies that provide services to individuals need to customize their solutions or configure the interactions with their customers. Such companies need to store customers’ PII data for identification, personalization; thereby the need to comply with Soc 2 Type 2.  
The final goal of this project was to build a comprehensive solution that provides a simple way to enable small businesses to gain the required compliance without expensive or cumbersome solutions. 
## Solution Statement:  
A secure system outlined below uses cryptographic techniques tailored to the needs of small businesses, provides easy integration options and easy installation, and enables compliance. 

### Easy installation:
The system allows one-click-install. A single action from the user will set up the application and run time service. The system will do self-checks and start the encryption engine and API endpoints.    
### Ease of integration:
The solution will leverage mutual TLS (transport layer security) based on connections to secure, authenticate, and authorize the traffic. The solution provides simple integration options, such as REST endpoints over HTTPS. A sample client is provided, demonstrating the simplicity of integration. 

![diagram1](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram1.png)


## Detailed Architecture
The architecture included the following components:
1.	A server that exposes REST endpoints to store and retrieve secrets for each client. The server enforces mutual-TLS for REST communication.
2.	A sample client application that demonstrates the store and retrieve functionality with mutual-TLS

![diagram2](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram2.png)

## Technical Architecture of the Solution
Expanding on the previous architecture, let us further detail the technical architecture of the solution. The technology choices made were:
1.	A Java based application, built with Spring Boot
2.	Use of Java Cryptography Engine for encryption, decryption, hashing, and other cryptographic routines
3.	Use of Java Key Storage for storing secrets
4.	Embedded Redis to store secrets optionally writing it to disk.
5.	Only for proving the concept, use of Java keytool in lieu of a PKI infrastructure.
With these technology choices, the depiction of technical architecture follows.

![diagram3](https://github.com/binitaj/lynx/blob/main/src/main/resources/static/diagram3.png)


