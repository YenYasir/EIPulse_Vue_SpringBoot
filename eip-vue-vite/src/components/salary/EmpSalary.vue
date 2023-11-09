<script>
import axios from "axios";

export default {
    data() {
        return {
            products: [],
        }
    },
    methods: {
        // deleteProduct(id) {
        //     axios.delete(`http://localhost:8090/eipulse/product/${id}`).then((res) => {
        //         alert('刪除成功')
        //         this.loadProducts();
        //     }).catch((e) => {
        //         alert('刪除失敗')
        //     })
        // },
        loadProducts() {
            axios.get('http://localhost:8090/eipulse/products').then(res => {
                console.log(res.data)
                this.products = res.data;
            }).catch((e) => {
                console.log(e)
            })
        },
        // editProduct() {

        // }
    },
    watch: {
    },
    name: "Products",
    components: { ProductForm },
    mounted() {
        this.loadProducts();
    }
}
</script>

<template>
    <div class="card text-center div1 rounded">
        <div class="card-header">薪資資訊</div>
        <div class="card-body">
            <h1>All EmpSalryInfo</h1>
            <table class="table table-bordered">
                <thead>
                    <tr>
                        <th>員工編號</th>
                        <th>員工姓名</th>
                        <th>基本薪資</th>
                        <th>勞保投保級距</th>
                        <th>勞退自願提繳</th>
                        <th>健保投保級距</th>
                        <th>眷屬扶養人數</th>
                        <th>福利金扣繳(Y/N)</th>
                    </tr>
                </thead>
                <tbody>
                    <tr v-for="product in products" :key="product.id">
                        <td>{{ product.productName }}</td>
                        <td>{{ product.price }}</td>
                        <td>{{ product.stockQuantity }}</td>
                        <td>{{ product.description }}</td>
                        <td>{{ product.updatedAt }} </td>
                        <td><button class="btn btn-secondary mx-1" @click="editProduct(product.id)"><i
                                    class="bi bi-pencil-square"></i></button><button class="btn btn-secondary mx-1"
                                @click="deleteProduct(product.id)"><i class="bi bi-bucket"></i></button></td>
                    </tr>
                </tbody>
            </table>
        </div>
        <div class="card-footer text-body-secondary">
            Copyright ©  EIPulse科技 All Rights Reserved.
        </div>
    </div>
</template>

<style scoped></style>