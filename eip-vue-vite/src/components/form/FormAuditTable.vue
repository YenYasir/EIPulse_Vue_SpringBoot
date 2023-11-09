<template>
  <Table width="100%" border :columns="columns" :data="data"></Table>
</template>

<script setup>
import { resolveComponent } from 'vue';
const columns = [
  {
    title: '申請人',
    key: 'empId',
    width: 100,
    fixed: 'left'
  },
  {
    title: '申請類型',
    key: 'typeName',
    width: 100
  },
  {
    title: '申請日期',
    key: 'startDate',
    width: 100
  },
  {
    title: '狀態',
    key: 'statusName',
    width: 100
  },
  {
    title: 'Action',
    key: 'action',
    fixed: 'right',
    width: 160,
    render: (h, params) => {
      return h('div', [
        h(resolveComponent('Button'), {
          type: 'text',
          size: 'small',
          style: {
            marginRight: '5px'
          },
        }, {
          default() {
            return 'View'
          }
        }),
        h(resolveComponent('Button'), {
          type: 'text',
          size: 'small'
        }, {
          default() {
            return 'Edit'
          }
        })
      ]);
    }
  }
];
const data = [
  {
    name: 'John Brown',
    age: 18,
    address: 'New York No. 1 Lake Park',
    province: 'America',
    city: 'New York',
    zip: 100000
  },
  {
    name: 'Jim Green',
    age: 24,
    address: 'Washington, D.C. No. 1 Lake Park',
    province: 'America',
    city: 'Washington, D.C.',
    zip: 100000
  },
  {
    name: 'Joe Black',
    age: 30,
    address: 'Sydney No. 1 Lake Park',
    province: 'Australian',
    city: 'Sydney',
    zip: 100000
  },
  {
    name: 'Jon Snow',
    age: 26,
    address: 'Ottawa No. 2 Lake Park',
    province: 'Canada',
    city: 'Ottawa',
    zip: 100000
  }
]
</script>

<style scoped></style>