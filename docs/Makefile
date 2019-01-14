include Makefile.macros

# voeg eventueel subdisr toe die ook een asciidoctor run nodig hebben
# Die hebben dan ook een Makefile nodig. Zie als voorbeeld de Makefile onder restricted.
all: index.html

index.html: README.adoc css/asciidoctor.css src/*.java
	asciidoctor -n -a linkcss -a stylesdir=css -a stylesheet=asciidoctor.css README.adoc -o index.html
