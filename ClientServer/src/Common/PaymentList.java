/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

/**
 *
 * @author CHUWI
 */
public class PaymentList extends ObjectList<BankTransaction>{
    public PaymentList(){
        super();
    }
    
    public void AddBankTransaction(BankTransaction payment){
        super.Put(payment.GetNumber(), payment);
    }
    
    public Boolean Validate(BankTransaction payment){
        BankTransaction p2 = super.Get(payment.GetNumber());
        if( p2 != null ){
            return p2.IsEqual(payment);
        }
        return null;
    }
}
