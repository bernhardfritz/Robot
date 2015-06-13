% UIBK Intelligent and Interactive Systems Report Class.
%
% # Based on the 'article' class.
% # Course number is input as a class option.
% # Uses A4 paper by default.
% # Uses 11pt type by default.
% # Redefines 'plain' pagestyle:
%   - header = course number+name and date.
%   - footer = page number.
%
% Authors:
% # 2011 Justus Piater
% # 2012 Stephen Cobeldick
%
% justus[dot]piater[at]uibk[dot]ac[dot]at
%
% ### Options and Article Class ###
%
% These commands can be used in .tex:
\newcommand*{\uibkclass}{iisreport}
\newcommand*{\uibkversion}{0.2}
\newcommand*{\uibkreleased}{2013/03/13} % LaTeX date format: yyyy/mm/dd
%
\NeedsTeXFormat{LaTeX2e}
\ProvidesClass{\uibkclass}[\uibkreleased\space\uibkversion\space UIBK IIS Report Class]
%
\newcommand*{\@uibkn}{\textcolor{red}{Course number as class option!}}
\newcommand*{\@uibkc}{}
\newcommand*{\@uibkcourse}[2]{\DeclareOption{#1}{\xdef\@uibkn{#1}\xdef\@uibkc{#2}}}
% Courses:
\@uibkcourse{703031}{Introduction to Autonomous and Intelligent Systems}% No longer than this name!
\@uibkcourse{703035}{Vertiefungsseminar}
\@uibkcourse{703037}{Vertiefungsseminar}
\@uibkcourse{703103}{Introduction to Computer Vision}
\@uibkcourse{703108}{Introduction to Machine Learning}
\@uibkcourse{703524}{Master Seminar 1}
\@uibkcourse{703525}{Master Seminar 2}
\@uibkcourse{703605}{Master Seminar 1}
\@uibkcourse{703611}{Selected Topics in Computer Vision}
\@uibkcourse{703612}{Programming Project Computer Vision}
\@uibkcourse{703643}{Advanced Machine Learning}
\@uibkcourse{703644}{Advanced Machine Learning}
\@uibkcourse{703645}{Probabilistic Models and Inference}
\@uibkcourse{703918}{Autonome Systeme}
\@uibkcourse{703939}{Seminar 2}
\@uibkcourse{703956}{Seminar 1}
%
% TypeSize:
\newcommand*{\@uibktypesize}{11pt} % 11pt UIBK standard.
\DeclareOption{10pt}{\edef\@uibktypesize{\CurrentOption}}
\DeclareOption{11pt}{\edef\@uibktypesize{\CurrentOption}}
\DeclareOption{12pt}{\edef\@uibktypesize{\CurrentOption}}
%
% All other options:
\DeclareOption*{\PassOptionsToClass{\CurrentOption}{article}}
\ProcessOptions\relax
%
\LoadClass[a4paper,\@uibktypesize]{article}
%
\RequirePackage{xcolor}%      Error message for course-number class-option.
\RequirePackage{natbib}%      Bibliography style.
\RequirePackage{hyperref}%    Hyperlinks.
\RequirePackage[all]{hypcap}% Hyperlinks to top of floats.
%
% ### Bibliography Style, Autoref Names, Default Pagestyle ###
%
\setcitestyle{round}
\bibliographystyle{plainnat}
%
\renewcommand{\equationautorefname}{equation}
\renewcommand{\figureautorefname}{figure}
\renewcommand{\tableautorefname}{table}
%
\pagestyle{headings}
%
% ### Course Number and Name ###
%
% - Provides a command \course{number}{name} to set course details.
%\newcommand*{\course}[2]{% {Number}{Name}
%  \renewcommand*{\@uibkn}{#1}%
%  \renewcommand*{\@uibkc}{#2}%
%}
%\@onlypreamble\course
%
% ### Title Page ###
%
% Plain pagestyle header + footer:
\def\ps@plain{%
  \def\@oddhead{\@uibkn\space\@uibkc\hfil\today}
  \let\@evenhead\@oddhead%
  \def\@oddfoot{\hfil\thepage\hfil}
  \let\@evenfoot\@oddfoot%
  \let\@mkboth\@gobbletwo
  \let\sectionmark\@gobble
  \let\subsectionmark\@gobble
}
%
% Title page (as per LaTeX, but without the date)
\def\@maketitle{%
  \newpage\null%
  \begin{center}%
  \let\footnote\thanks
    {\LARGE\@title\par}%
    \vskip 1.5em%
    {\large%
      \lineskip .5em%
      \begin{tabular}[t]{c}%
        \@author%
      \end{tabular}\par}%
  \end{center}%
  \par%
  \vskip 1.5em
}
%
% ### Correct English Date Format ###
%
\newcommand*{\@uibktoday}{%
  \number\day%
  \ifcase\day%
    \or st\or nd\or rd\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or th\or st\or nd\or rd\or th\or th\or th\or th\or th\or th\or th\or st%
  \fi%
  \space%
  \ifcase\month%
    \or January\or February\or March\or April\or May\or June\or July\or August\or September\or October\or November\or December%
  \fi%
  \space%
  \number\year%
}
\let\today\@uibktoday
\AtBeginDocument{%
  \def\dateuk{\let\today\@uibktoday}
  \def\dateenglish{\let\today\@uibktoday}
  \def\datebritish{\let\today\@uibktoday}
  \def\dateUKenglish{\let\today\@uibktoday}
  \def\dateaustralian{\let\today\@uibktoday}
  \def\datenewzealand{\let\today\@uibktoday}
}
%
% End of iisreport.cls
