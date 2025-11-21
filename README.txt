Aide à la décision et intelligence artificielle - Rendu CC3

AGBOHOUTO Jody-Ange 22310270
-Groupe TP 2A
-Chargé de TP: M BONNET Grégory

Classes éxécutables:
-modelling.Demo
-modelling.Test

-planning.Demo
-planning.Test

-cp.Demo
-cp.Test

-datamining.Demo
-datamining.Test

-blocksworld.demo.*

Information sur la compilation et l'exécution 

Commande pour la compilation: 
$ javac -d build -cp "lib/blocksworld.jar:lib/bwgenerator.jar:lib/tests.jar" src/*/*.java src/*/*/*.java

Commandes pour éxecuter les tests du package blocksworld:
java -cp build blocksworld.demo.DemoExo5
java -cp build blocksworld.demo.DemoExo8
java -cp build blocksworld.demo.DemoExo9
java -cp build blocksworld.demo.DemoExo10
java -cp build:lib/blocksworld.jar:lib/bwgenerator.jar:lib/tests.jar blocksworld.DemoExo12

Pour pour éxecuter tout les autres packages:
java -cp $ java -cp ".:./build:./lib/tests.jar" nomDuPackage.NomDeLaClasse

----------------------------NOTE----------------------------
-Placer le fichier .jar des tests dans le repertoire /lib avant la compilation


