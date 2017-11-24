package br.ufc.russas.n2s.darwin.controller;

import br.ufc.russas.n2s.darwin.beans.SelecaoBeans;
import br.ufc.russas.n2s.darwin.model.FileManipulation;
import br.ufc.russas.n2s.darwin.service.SelecaoServiceIfc;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Wallison Carlos
 */
@Controller("selecaoController")
@RequestMapping(value = "/selecao")
public class SelecaoController {
    
    private SelecaoServiceIfc selecaoServiceIfc;
    
    @Autowired(required = true)
    public void setSelecaoServiceIfc(@Qualifier("selecaoServiceIfc")SelecaoServiceIfc selecaoServiceIfc){
        this.selecaoServiceIfc = selecaoServiceIfc;
    }

    @RequestMapping(value = "/{selecaoCodigo}", method = RequestMethod.GET)
    public String getIndex(@PathVariable String selecaoCodigo, Model model, HttpServletRequest request){
        String[] part = selecaoCodigo.split("_");
        long codSelecao = Long.parseLong(part[part.length-1]);
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        request.getSession().setAttribute("selecao", selecao);
        return "selecao";
    }
    
    @RequestMapping(value = "/{selecaoCodigo}/edital}", method = RequestMethod.GET)
    public void generateReport(@PathVariable String selecaoCodigo, HttpServletResponse response) throws Exception {
        String[] part = selecaoCodigo.split("_");
        long codSelecao = Long.parseLong(part[part.length-1]);
        SelecaoBeans selecao = selecaoServiceIfc.getSelecao(codSelecao);
        System.out.println("teste");
        byte[] data = FileManipulation.getBytes(selecao.getEdital().getArquivo());

        streamReport(response, data, selecao.getEdital().getTitulo());
    }

    private void streamReport(HttpServletResponse response, byte[] data, String name)
            throws IOException {

        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "attachment; filename=" + name);
        response.setContentLength(data.length);

        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }
}
