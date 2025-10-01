Structure of a Message :
{ 
	!type : type of component - barChart, sparkLine, progressBar... 
  	!action : 'create' || 'update' ||...	
  	?id : id of the component 
  	!data : {}  json object that contains all the data of a component
 } 
 
 ! required
 ? not required
 
Only a component with an id can be updated
 

 
 a component is defined by its type
 
 ====Type of Component===
 #id 
 {
  data or content 
 {
 ====
 
 We can create a component or update a component defined by its #id.
 
 Exemple a table 
 === Table of countries ===
 #id 
 data : {
 	 labels :[]
 	 data : [] //rows
 	 page : 1
 	 count : 567 
 	 sort : {col 1, asc}
 }
 
 le style définit le RPP. (Rows Per Page) 
si l'utilisateur prend la page 2 alors coté serveur on recalcule les datas. Et on renvoie toutes les datas de la table.
Ensuite il n'y a plus qu'à rafraichir coté client.
 
 
Cas d'un autocomplete 
=== AutoComplete selecteur de Pays ===
 #id 
 data : {
 	input:	
 	labels :[]
 }
 
Lorsque l'utilisateur modifie input on modifie les labels proposés.
 
 
 