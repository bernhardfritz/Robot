# LaTeX Makefile
FILE=Documentation_FritzKochZelger
all: $(FILE).pdf
.PHONY: clean
clean:
	rm *.aux *.blg *.out *.bbl *.log *.gz
$(FILE).pdf: $(FILE).tex
	pdflatex $(FILE)
	bibtex $(FILE)
	pdflatex $(FILE)
	pdflatex $(FILE)
