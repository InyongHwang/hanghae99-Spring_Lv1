### API명세

| Method | URL             | Request                                                                                                    | Response                                                                                                                                                                                                                                                                         |
|--------|-----------------|------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| GET    | /api/posts      | -                                                                                                          | {<br>{<br>"createAt": "2023-04-15T14:00:00.226062",<br>"username": "username",<br>"title": "title",<br>"contents": "contents"<br>},<br>{<br>"createAt": "2023-04-15T14:01:00.226064",<br>"username": "username",<br>"title": "title",<br>"contents": "contents"<br>}<br>...<br>} |
| POST   | /api/posts      | {<br>"username": "username",<br>"password": "password",<br>"title": "title",<br>"contents": "contents<br>} | {<br>"username": "username",<br>"password": "password",<br>"title": "title",<br>"contents": "contents"<br>} |
| GET    | /api/posts/{id} | -                                                                                                          | {<br>"createAt": "2023-04-15T14:00:00.226062",<br>"username": "username",<br>"title": "title",<br>"contents": "contents"<br>} |
| PUT    | /api/posts/{id} | {<br>"username": "username2",<br>"password": "password2",<br>"title": "title2",<br>"contents": "contents2<br>} | {{<br>"createAt": "2023-04-15T14:00:00.226062",<br>"username": "username2",<br>"password": "password2",<br>"title": "title2",<br>"contents": "contents2"<br>} |
| DELETE | /api/posts/{id} | {<br>"password": "password"<br>}                                                                           | {<br>"success": true<br>}                                                                                                                                                                                                                                                        |
---

### Use Case
![image](https://user-images.githubusercontent.com/123296558/232387410-3cb8400f-5679-444c-a721-86399480f280.png)

---

### ERD
![image](https://user-images.githubusercontent.com/123296558/232786536-b252c74d-5349-4a2a-bb09-d9ecdbf405d5.png)
