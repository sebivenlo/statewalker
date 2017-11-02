include Makefile.macros

# voeg eventueel subdisr toe die ook een asciidcotor run nodig hebben
# Die hebben dan ook een Makefile nodig. Zie als voorbeeld de Makefile onder restricted.
all: index.html

index2017.html: index2017.adoc css/asciidoctor.css 
	asciidoctor -n -a linkcss -a stylesdir=css -a stylesheet=asciidoctor.css index.adoc
