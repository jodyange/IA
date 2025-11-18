Aide à la décision et intelligence artificielle

-Groupe TP 2A
-Chargé de TP: M BONNET Grégory
-Binôme:
    1- ADEKOYA Boluwatife 22311964
    2- AGBOHOUTO Jody-Ange 22310270




Classes éxécutables:
-cp.Demo
-cp.Test

-datamining.Demo
-datamining.Test

Information sur la compulation et l'execution 
1- Extraire le fichier 
2- Se placer dans le dossier src 
3- Ouvrir un terminal et taper les commandes suivantes :

# package cp:
	compilation: javac -d build -cp lib/tests.jar cp/*.java
    Test: java -cp " ../build:../lib/tests.jar cp.Test


# package datamining:
    compilation: javac -d ../build -cp ../lib/tests.jar datamining/*.java 
    Test: java -cp ../build:../lib/tests.jar datamining.Test



----------------------------NOTE----------------------------
-Placer le fichier .jar des tests dans le repertoire /lib avant la compilation


