1. Start the PostgreSQL DB (in this project the DB was containerised in Docker with this command: 'docker run -d --rm --name postgresdb -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=postgres -p 5432:5432 -v pgdata:/var/lib/postgresql/data postgres')
2. Compile and run the backend in IntelliJ
3. Open Terminal at the /frontend path and run 'npm start' to start the frontend. Default prot should be 3000, but if already in use it will open on 3001
