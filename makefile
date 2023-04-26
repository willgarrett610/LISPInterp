JAVAC = javac
SRC = ./src
BIN = ./bin

all: lisp

lisp: classes

classes:
	$(JAVAC) -d $(BIN) -sourcepath $(SRC) $(SRC)/main/Main.java

jar: classes
	@echo "Manifest-Version: 1.0" > manifest.txt
	@echo "Main-Class: main.Main" >> manifest.txt
	@echo "" >> manifest.txt

	jar -cvmf manifest.txt lisp.jar -C $(BIN)/ .

run: classes
	java -cp $(BIN)/ main/Main

clean:
	rm -fr $(BIN)
	rm -f manifest.txt
	rm -f lisp.jar