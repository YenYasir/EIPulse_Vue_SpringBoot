import {defineStore} from "pinia";

export const mallStore=defineStore({
    id:'mall',
    state:()=>({
        addCartItem:false,
        changeOrderPage:false,
    }),
    getters:{

    },
    actions:{
        setAddCartItem(addStatus){
            this.addCartItem =addStatus
        },
        setChangeOrderPage(change){
            this.changeOrderPage =change;
        }
    }
})