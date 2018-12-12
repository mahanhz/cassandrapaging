# Cassandra paging issue

### Description

If you read all the rows in cassandra into a single Slice then the hasNext method will still return true.

This is because it only uses paging state.

However if you combine paging state and isExhausted then Cassandra will know that there are no more rows left.


1. **docker-compose up -d** to start up cassandra

2. **./gradlew bootRun** to start up the app

3. There will now be 5 rows in the cust_t table

4. http://localhost:8080/repo?limit=5 will return: **This slice has more pages**

5. http://localhost:8080/resultset?limit=5&useIsExhausted=false: **This slice has more pages**

6. http://localhost:8080/resultset?limit=5&useIsExhausted=true: **There are no more pages**