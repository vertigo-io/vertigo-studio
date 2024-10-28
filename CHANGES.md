Version history
===============

Running 4.3.0
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-420-to-430)
  
more to come :)


Running 4.2.0 - 2024/07/04
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-410-to-420)

* **[Studio] Apply renamings from Core**
* **[Studio] Rename dtDefinitionA/B to dataDefinitionA/B in NN annotation**
* [Studio] Support "classpath:" protocol in resources path
* [Studio] Fix : Don't generate dtResources for external package
* Update libs 
  - jakarta.servlet-api 5.0.0 -> 6.1.0
  - c3p0 0.9.5.5 -> 0.10.1
  - slf4j 1.7.36 -> 2.0.13


Release 4.1.0 - 2023/11/09
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-400-to-410)
* [Studio] Add Standard NamedThreadFactory for ExecutorsServices
* [Studio] Add usefull getSearchindexDefinition in generator
* Update libs 
  - h2 2.2.220 -> 2.2.224


Release 4.0.0 - 2023/08/17
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-360-to-400)
* [Core] Replace value object class to record when possible
* [Core] Rename record's getter from getMyProperty to myProperty()
* [Studio] Fix tableName for SQL generation
* Update versions :
  - freemarker 2.3.31 -> 2.3.32
  - servlet 4.0.1 -> 5.0.0 (jakarta)
  - h2 2.1.214 -> 2.2.220
  - slf4j 1.7.33 -> 1.7.36
  
Release 3.6.0 - 2023/05/04
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-350-to-360)
* [Mermaid] change to v10
* [generator] filter generation based on projectPackageName
* [generator] create sequence `cache 1` (default cache value) on postgre
  - Performance improvements is more that negligeable in PostgreSQL and the pk field cannot be used safely as an order
* [generator] support null values in masterData data fields
* [Core] fix update snakeyaml 1.33 -> 2.0
  

Release 3.5.0 - 2023/01/06
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-340-to-350)
__no changes in studio__

Release 3.4.0 - 2022/10/12
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-330-to-340)
* Fix tableName for SQL generation
* [TaskTests] fix taskTests generator : package name and set field value
* [mermaid] better direction BT : parents dependencies are on top
* [generator] properties in UTF-8 since java 9
* [model] fix generation of fragments
* fix optional cardinality on alias
* taskoutput attribute has a name
* update dependencies
  - h2: 2.1.210 -> 2.1.214

Release 3.3.0 - 2022/02/03
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-320-to-330)
* Centralize XML security in XmlUtil
* SearchClient is now DtIndex oriented (result structure) +Link FacetedQuery To DtIndex
* add a generator to create ksp from other sources (like oom or xmi)
* fixed a bug when the sortField or the displayField is a computed
* add keyField on dtSketch
* Fix tableName of NN : const case
* Fix tableName in constCase* 
* __Change detection of Association code : must have CamelCase AFTER 2 trigrams__
* Fix association code name with number (accept XxxYyy123)
* [Task] Keep \n in request into SQL request
* update dependencies
  - h2: 2.0.206 -> 2.1.210
  - slf4j 1.7.30 -> 1.7.33

Release 3.2.0 - 2021/06/21
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-311-to-320)
* [Mermaid] zoom at pointer position
* [Authorization] Fix uniqueness of operation sketch

Release 3.1.1 - 2021/02/22
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-310-to-311)
__no changes in studio__

Release 3.1.0 - 2021/02/05
----------------------
[Migration help](https://github.com/vertigo-io/vertigo/wiki/Vertigo-Migration-Guide#from-300-to-310)
* [Studio] Fix missing cardinality on ForeignKey

Release 3.0.0 - 2020/11/20
----------------------
__no changes in studio__

Release 2.1.0 - 2019/11/12
----------------------
* [Studio] unused attribute
* [studio] fix generated javadoc for xAO

Release 2.0.0 - 2019/03/22
----------------------
* [Studio] unused attribute
* [studio] multiple files for sql init of staticmasterdatas
* [studio] dt objects can be splitted by feature
* [studio] fix dao import when dt_index = keyconcept
* [studio] created a SearchClient component dedicated to search access
* [studio] Drop if exists
* [studio] Removed sequences for non numeric PK

Release 1.1.3 - 2019/03/21
----------------------
* [Studio] unused attribute

Release 1.1.2 - 2018/06/28
----------------------
__no changes in studio__

Release 1.1.1 - 2018/04/27
----------------------
* [studio] fix security generator
* [Studio] Fixed TS mda
* [Studio] Fixed computed fields for properties and enum

Release 1.1.0 - 2017/12/07
----------------------
* [studio] static masterdata are accessed by an enum (via a dedicated accessor)
* [studio] added sql generation for masterdata 
* [studio] Updated Typescript generation for Focus4 (Node, Entity and masterData)
* [studio] Task with one input of Data-Object are DAO instead of PAO
* [studio] Changed sequence generator name in JPA annotation to be unique 

Release 1.0.0 - 2017/07/07
----------------------
* [studio] move in vertigo repo
*	[studio] refactored freemarker
*	[studio] refactored all models (models, source objects and templates are completely independants)
*	[studio] Fixed JPA annotations for Hibernate and its "special" sequences
*	[studio] Added TS generator

Release 0.9.4a - 2017/03/15
----------------------
* [Studio] Fix Studio issue 

Release 0.9.4 - 2017/03/13
----------------------
[Migration help](https://github.com/KleeGroup/vertigo/wiki/Vertigo-Migration-Guide#from-093-to-094)

__In Bold__ : Potential compatibility problems 
* [All] Code cleaning, refactoring and documenting (and Stream java8, Optionnal, Methods refs, ...)
* [All] Always use WrappedException (wrap & unwrap), and params order changed
* [All] Moved dsl classes from core to dynamo
* [Studio] Added multi databases crebase.sql scripts
* [Studio] Fixed #6 ([PerformanceMetricEngine] Failed to execute tasks after one exeption on transaction)
* [Studio] Renamed readForUpdate to readOneForUpdate
* [Studio] Added operations enum to generated classes



Release 0.9.3 - 2016/10/11
----------------------
[Migration help](https://github.com/KleeGroup/vertigo/wiki/Vertigo-Migration-Guide#from-092-to-093)

__In Bold__ : Potential compatibility problems 
* [All] Code cleaning, refactoring and documenting
* __[Studio] Renamed Role enum to Roles__
* [Studio] Fixed stereotype package
* [Studio] Removed persistence property from dtDefinition
* [Studio] Fixed getUri annotations for JPA
* [Studio] Added FK to Entity in Fragments
* [Studio] Renamed stereotype Data to ValueObject (always default sterotype)
* [Studio] Added DataSpace annotation
* [Studio] Fixed KleeGroup/vertigo#72
* [Studio] Fixed KleeGroup/vertigo#68
* [Studio] Fixed replace Option by jdk Optional


Release 0.9.2 - 2016/06/28
----------------------
[Migration help](https://github.com/KleeGroup/vertigo/wiki/Vertigo-Migration-Guide#from-091-to-092)

__In Bold__ : Potential compatibility problems 
* [All] Code cleaning, refactoring and documenting
* [All] Updated 3rd party libs versions (freemarker)
*  [Studio] Fixed #58 missing SQL primary key
*  [Studio] Added SQLServer support (use param `baseCible:SqlServer` of `SqlGeneratorPlugin`)
* __[Studio] Renamed CRUD methods__ (get => read , getList => findAll)
* __[Studio] Aligned vertigo Option api to JDK api__ (`isPresent`, `ofNullable`, `orElse`, `of`)
*  [Studio] Added test of crebase.sql script on an mem H2 database
* [Studio] Refactored File generation
* __[Studio] Renamed templates path from `templates` to `template`__


Release 0.9.1 - 2016/02/05
----------------------
