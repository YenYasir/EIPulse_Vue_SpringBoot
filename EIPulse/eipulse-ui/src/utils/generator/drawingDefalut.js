export default [
  {
    layout: 'colFormItem',
    tagIcon: 'input',
    label: '手機號',
    vModel: 'mobile',
    formId: 6,
    tag: 'el-input',
    placeholder: '請輸入手機號碼',
    defaultValue: '',
    span: 24,
    style: { width: '100%' },
    clearable: true,
    prepend: '',
    append: '',
    'prefix-icon': 'el-icon-mobile',
    'suffix-icon': '',
    maxlength: 11,
    'show-word-limit': true,
    readonly: false,
    disabled: false,
    required: true,
    changeTag: true,
    regList: [{
      pattern: ' /^09\d{2}?\d{3}?\d{3}$/',
      message: '手機號碼格式錯誤'
    }]
  }
]
