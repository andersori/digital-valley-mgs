<%-- 
    Document   : menu-superior
    Created on : 06/10/2017, 10:29:03
    Author     : Alex Felipe
--%>
<%@page import="br.ufc.russas.n2s.darwin.model.EnumPermissoes"%>
<%@page import="br.ufc.russas.n2s.darwin.model.EnumPermissoes"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.ufc.russas.n2s.darwin.beans.UsuarioBeans"%>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <a class="navbar-brand" href="#">
        <img src="https://png.icons8.com/natural-food/color/160" width="30" height="30" class="d-inline-block align-top" alt="">
        Darwin
    </a>  
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
        <div class="navbar-nav">
            <a class="nav-item nav-link" href="/Darwin">In�cio <span class="sr-only">(current)</span></a>
        	<a class="nav-item nav-link" href="#">Minhas sele��es</a>                                
            <a class="nav-item nav-link" href="/Darwin/cadastrarSelecao">Cadastrar Sele��o</a>
            <a class="nav-item nav-link" href="#">Sair</a>
        </div>
    </div>
</nav>