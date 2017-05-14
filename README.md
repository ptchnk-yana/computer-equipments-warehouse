# computer-equipments-warehouse
ONPU diplom for Mironov

It's a desktop application for managing devices in organization, basically in university.

### Build
[Maven](https://maven.apache.org/) is used as a project management system, so to build the project install it and run
```sh
mvn clean install
```

### Run
Default build has assembly plugin, so after the build you will have executable jar with dependencies.
There are following command line arguments:
* `--lnf LoockAndFeelClass` set loock and feel. See more on https://docs.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
* `--data path` set paht to data storage (location of database files). By default it's `${user.home}/.mironov_dyplom`
* `--help` display help message and exit
* `--mock_init` delete all data from the database and insert there presentation data (for demos or other quick trials)

So, to explore the system you can run it in following way:
```sh
java -jar target/computer-equipments-warehouse-1.1-SNAPSHOT-jar-with-dependencies.jar --mock_init
```

### Database
[H2](http://www.h2database.com/html/main.html) embeded database engine is used to store data. Database location and rest configuration is  stored in [config file](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/resources/application.properties).

#### AutoPatch
For creating database schema [AutoPatch](https://github.com/tacitknowledge/autopatch) is used.

### Data structure
The main data structure of the project is a [device](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/Device.java) of some [type](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/DeviceType.java) which located in a [room](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/Room.java) of a [building](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/Building.java) (campus). Each room is managed by some [user](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/User.java).

The communications between [operators and administrators](https://github.com/ptchnk-yana/computer-equipments-warehouse/blob/master/src/main/java/onpu/diplom/mironov/cew/bean/UserPrivilege.java) are based on system requests: each user can create a request and one of the admins should resolve it _(simple [issue tracking system](https://en.wikipedia.org/wiki/Issue_tracking_system))_.
```
Building 1---* Room 1---* Device 1---* DevectType

    User 1---* Room

    User *---* Request
```

---
 
 ![Project connected image](https://fedora.digitalcommonwealth.org/fedora/objects/commonwealth:sn00b1354/datastreams/access800/content)

