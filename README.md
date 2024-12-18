# CompareD

 Para el despliegue de la aplicación deberemos crear un docker-compose.yml en la raíz de la aplicación que deberá incluir
 ## docker-compose.yml
 
    version: '3.8'

    services:
    
    postgres:
    
      image: postgres:14
    
    container_name: compared_db
    
    restart: always
    
    environment:
    
      POSTGRES_USER: admin
      
      POSTGRES_PASSWORD: adminpassword
      
      POSTGRES_DB: postgres
      
    volumes:
    
      - postgres_data:/var/lib/postgresql/data
      
    ports:
    
      - "5432:5432"
      

    volumes:
 
    postgres_data:
  
    driver: local
    
   
Una vez tengamos nuestro archivo .yml creado iremos con la consola a la raíz de nuestro proyecto y ejecutaremos el comando 
docker-compose up -d

Ahora veremos si nuestro contenedor está arrancado en Docker, si es así, entonces procederemos a arrancar el back usando
IntelliJ y para arrancar Vue.js nos iremos a la carpeta del proyecto de front en este caso /frontend y abriremos la terminal
y escribiremos npm run serve.

Y así ya tendremos nuestra aplicación funcionando
