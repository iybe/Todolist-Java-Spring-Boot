# TODOLIST

### Como executar

- Execute o comando abaixo a partir da raiz do projeto, para criar uma imagem docker da aplicação
```
docker build -t todolistjava .
```

- Execute o comando abaixo a partir da raiz do projeto, para criar/usar um container postgresql com volume e que sera usado como banco de dados

```
docker-compose up
```

- Execute o comando abaixo a partir da raiz do projeto, para instanciar um container com a imagem da aplicação

```
sudo docker run -it --rm --network host todolistjava sh -c "java -jar app.jar local"
```