## Choose between 반대 or 찬성!!, 반찬(Banchan)📝
![대표사진](exec/resources/banchanMhome.png)
## *"주민 여러분께 알립니다. 오후 6시 201동 105호에서 반상회가 있을 예정이오니 늦지않게 참가 바랍니다."*


## 목차
1. [기획 의도 및 기대 효과](#기획 의도 및 기대 효과)
2. [개발 환경](#개발-환경)
3. [주요 기능](#주요-기능)
4. [기술 소개](#기술-소개)
5. [설계 문서](#설계-문서)
6. [팀원 소개](#팀원-소개)

## 1. 기획 의도 및 기대 효과
지역 주민들이 직접 모여 의견을 나누고, 중요한 사안을 논의하는 "전통적인 반상회"는 주민들의 결속력을 강화하는 중요한 역할을 해왔으며 주민들의 목소리를 빠른 시일내에 반영하였습니다. 하지만 현재 바쁜 일상과 시간적 제약, 그리고 사회적 분위기로 인해 참석을 꺼려 하는 경우가 많습니다. 이에 따라, 온라인을 통해 누구나 쉽게 참여할 수 있는 새로운 형태의 반상회 플랫폼 "반찬"을 기획하게 되었습니다.

"반찬"은 다양한 기술을 통해 물리적 제약을 넘어서는 소통의 장을 마련하고자 하며 주민들은 언제 어디서나 손쉽게 참여할 수 있으며, 다양한 의견을 실시간으로 나누고 공동의 결정을 내릴 수 있습니다. 특히, 온라인 주민투표 기능을 통해 지역 사회의 중요한 이슈에 대해 모든 주민이 평등하게 의견을 제시하고, 온라인 화상 주민회의를 통해 직접 대면하지 않더라도 서로의 생각을 나눌 수 있는 기회를 제공합니다.

"반찬"은 주민 간의 소통을 강화하여 더 나은 공동체를 만들기 위한 도구로서 기획되었습니다. 궁극적으로는 지역 사회의 모든 구성원이 자신의 목소리를 낼 수 있는 평등한 플랫폼을 제공하여, 보다 협력적이고 민주적인 지역 사회를 실현하고자 합니다.

## 2. 개발 환경
### Frontend
| Name | Version |
| --- | --- |
| Typescript | 5.2.2 |
| React | 18.3.1 |
| NodeJs | 20.16.0 |

### Backend
| Name | Version |
| --- | --- |
| Java | 17 |
| Spring Boot | 3.3.2 |
| MySql | 8.0 |
| Redis | 7.4.0 |

### Infra
| Name | Version |
| --- | --- |
| Docker | 27.1.1 |
| Docker Compose | 2.29.1 |
| Nginx | 1.27.0 |

### CI/CD
| Name | Version |
| --- | --- |
| Jenkins | 2.462.1 |

## 3. 주요 기능



## 4. 기술 소개
    1. Google STT(Speech-to-Text) API
        - 서비스 이름: Google Cloud Speech-to-Text API
        - 사용 목적 : 음성을 텍스트로 변환하여 사용자 입력을 처리
        - 사용 방법 : 
            - Google Cloud Platform에서 계정 생성 후 프로젝트 생성.
            - Speech-to-Text API를 해당 프로젝트에 활성화
            - API 키 또는 서비스 계정을 생성하여 인증 정보 확보.
        - API KEY : 보안상 API 키는 별도 관리
        - 활용 방법 : 
            - 음성테이터를 STT를 이용하여 텍스트 변환
            - 1분 이상의 오디오의 경우 GCP 업로드가 필수적으로 요구되므로 비동기로 오디오 분할
            - 기존의 오디오 파일을 전처리 (모노 채널 설정 및 샘플링 주파수 설정)
            - 텍스트화 된 음성파일을 txt 파일로 저장하여 추후에 활용
    
    2. GPT-4o mini
        - 서비스 이름: GPT-4o mini
        - 사용 목적 : 텍스트 데이터를 기반으로 한 자연어 처리 및 텍스트 요약
        - 사용 방법 : 
            - OPENAI API 키 발급
        - API KEY : 보안상 API 키는 별도 관리
        - 활용 방법 : 
            - 구글 STT API로 변환된 텍스트를 GPT-4o mini에 전송하여 자연어 이해 및 대화 응답 생성

    3. 카카오 소셜 로그인
        - 서비스 이름: Kakao Social Login (카카오 소셜 로그인)
        - 사용 목적: 사용자들이 카카오 계정을 사용해 손쉽게 로그인할 수 있도록 함
        - 가입 절차:
            - Kakao Developers에서 계정 생성 후 애플리케이션 등록.
            - '카카오 로그인' 기능을 활성화하고 Redirect URI 설정.
            - JavaScript 키 및 REST API 키 발급.
        - API KEY :
            - JavaScript 키: (보안상 API 키는 별도 관리)
            - REST API 키: (보안상 API 키는 별도 관리)
        - 활용 방법
            - 프론트엔드에서 JavaScript SDK를 사용해 로그인 버튼 구현
            - 사용자가 카카오 계정으로 로그인 시, 서버 측에서 REST API를 활용해 사용자 정보 조회

## 5. 설계 문서
### 와이어 프레임
![요구사항정의서](exec/resources/요구사항정의서.png)

### 요구사항 정의서
![요구사항정의서](exec/resources/요구사항정의서.png)

### API명세서
![API1](exec/resources/API1.png)
![API1](exec/resources/API1.png)