# Frontend server with nginx
1. In “package.json”, add a field “homepage”: “.”.
2. Run “npm run build” to build to package the project.
3. Upload all the files in the “build” folder to the path “/var/www/html” in the server.

```
scp -i .\id_geni_ssh_rsa -r D:\Distributed-Password-Cracker\react-app\build\* ziqi1756@pcvm2-39.genirack.nyu.edu:/users/ziqi1756
```
4. On the server, run “sudo apt-get update” and “sudo apt install nginx-full” to install the nginx.

```
sudo cp -r ./* /var/www/html

sudo apt-get update
sudo apt install nginx-full

sudo chmod 777 nginx.conf

vim /etc/nginx/nginx.conf
```
5. Edit the nginx configuration file in path “/etc/nginx/nginx.conf”.
Add the following code into http configuration.

```
http{
    # other code
    server {
        listen 80;
    
        location / { 
            root /var/www/html; 
            index index.html index.htm;
        }
    }
}
```

6. cp your file to /var/www/html
sudo cp -r ./build/* /var/www/html

7. Run “sudo service nginx start” to run nginx.  Other commands are useful, such as “sudo systemctl status nginx” to check the nginx status, “sudo service nginx reload” to restart the nginx service.
```
sudo nginx -t  

sudo service nginx start

sudo systemctl status nginx

sudo service nginx reload
```
