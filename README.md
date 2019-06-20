# Informações sobre o projeto Campaign Service

- A aplicação possui uma documentação que pode ser acessada através do link: http://localhost:8081/swagger-ui.html

- Para incializar a aplicação execute o comando:  ` mvn spring-boot:run`

- A aplicação também possui um banco MongoDB _embbeded_ que utiliza a porta: `56789`

- A porta utilizada pela aplicação é a: `8081` 

- A aplicação também possui um _broker_ _embbeded_ para gerenciamento de filas/tópicos.

## Tecnologias utilizadas:

- Java 8

- Maven: Na construção do projeto e gerenciamento dos _frameworks_ utilizados

- Spring Boot: Na construção do projeto _Spring_

- Spring Cloud Feign: Utilizado para a integração entre microserviços.

- Spring Cloud Hytrix: Utilizado para criação de métodos de fallback, caso um microserviço esteja _down_

- MongoDB: Utilizado como o banco de dados da aplicação, por se assemelhar ao tipo de dados que vão trafegar na aplicação.

- Swagger: Utilizado para documentar as chamadas do microserviço.

- AssertJ / JUnit / Mockito: Utilizados para a criação dos testes da aplicação.

- Spring Data MongoDB: Utilizado por conter uma interface de persistência que facilita a integração da aplicação com o banco de dados.

- Jackson: Utilizado na conversão de dados JSON e Java

- ActiveMQ: Utilizado para a criação de um tópico `campaign-topic-notification` que realiza a notificação, aos sistemas inscritos, de mudanças nas campanhas.

### Estratégias utilizadas para a construção do projeto

Para a criação do projeto, optei por utilizar alguns "padrões" de mercado, como o **Spring Boot**, que é bastante utilizado por ser um _framework_ de fácil utilização e com integrações para várias outras ferramentas. 

Já para banco de dados, optei pelo MongoDB, por se assemelhar com os tipos de dados trafegados na aplicação, juntamente com ele foi utilizado o **Spring Data MongoDB** que facilita a realização de CRUD's no sistema.

Já para a integração entre microserviços e estrategias de _fallback_ , segui na linha de utilizar os "padrões" do mercado
e utilizei o **Feign** e o **Hystrix** respectivamente.

Para realizar a notificação a outros microserviços, optei pelo ActiveMQ, por obter um cliente imbutido no Spring Boot.

Para seguir os requisitos do projeto, foi criado um serviço CRUD, que realiza:

* Busca todas as campanhas ativas, utilizando a data atual.
* Busca a campanha por ID.
* Busca de campanha ativas por time, utilizando o ID do time.
* Criação uma nova campanha.
* Atualização de uma campanha, utilizando o ID da campanha.
* Deleção de uma campanha, utilizando o ID da campanha.

O serviço pode ser executado utilizando a documentação no **Swagger**, o formato suportado pela aplicação é somente **JSON**. 

- O formato para a inserção de datas é o ``YYY-MM-DD``.

#### Informações a respeito dos pacotes e classes do projeto:

Todas as classes possuem _Javadoc_ para facilitar o entendimento do código.