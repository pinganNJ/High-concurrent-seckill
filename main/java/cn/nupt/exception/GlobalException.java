package cn.nupt.exception;

import cn.nupt.result.CodeMsg;

/**
 * @AUTHOR PizAn
 * @CREAET 2019-07-25 22:02
 */

public class GlobalException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private CodeMsg cm;
    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm=cm;

    }
    public CodeMsg getCm() {
        return cm;
    }
    public void setCm(CodeMsg cm) {
        this.cm = cm;
    }

}
