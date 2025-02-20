# Projeto_PA
Detalhes sobre a estrutura de dados desenhada para suportar o PFS:

-Para suportar o PFS decidimos utilizar uma estrutura de dados baseada no ADT Tree pois desta forma podemos representar as informações de forma hierárquica. Cada nó da árvore irá representar um elemento do tipo File ou Directory, sendo que caso seja directory poderá ter nós filhos. Outra razão para esta escolha foi porque ao utilizarmos uma "árvore" o processo para implementar as operações atomicas que nos foram pedidas como adicionar, remover, etc..., são facilitadas usando esta estrutura, devido a facilidade em percorrer os elementos da árvore.

Previsão de como irá ser persistida a árvore do PFS:

-A nossa previsão para a persistencia de dados será o armazenamento da informação num ficheiro csv ou json.

Descrição das duas funcionalidades extra que o grupo propõe implementar:

-As funcionalidades extras que o grupo se propõe a implementar são nomeadamente a recuperação de ficheiros excluídos e a listagem dos favoritos.

Resumo do trabalho/código desenvolvido:

-Foi criada 1 classe dedicada á interface gráfica e 4 classes dedicadas á implementação das estruturas de ficheiros/diretorios e árvore, com ainda a criação de 1 classe de teste para as operações pedidas no enunciado do projeto. 

Mockup indicado no ponto 4:

-A mockup da interface gráfica foi construida com apoio do paint e, está colocada na pasta mockup. 
