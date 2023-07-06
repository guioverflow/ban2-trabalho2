# Trabalho BAN2

Para executar esse projeto, é necessário ter o Docker.

Basta subir um container do neo4j por meio do terminal com o seguinte comando:

`docker run --publish=7474:7474 --publish=7687:7687 --volume=$HOME/neo4j/data:/data --env NEO4J_AUTH=neo4j/password neo4j:3.5.3`

![image](https://github.com/guioverflow/ban2-trabalho2/assets/84868817/ad2f93e4-f4d6-483d-8499-a7a389e9e5c2)

Com o container em execução, não há configurações adicionais necessárias, basta executar a função main do projeto na classe AtacadistaApplication e fazer uso do sistema.
