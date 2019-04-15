// https://cn.vuejs.org/v2/guide/
var app = new Vue({
  el: '#app',
  data: {
    message: 'Hello world!'
  }
});

var app2 = new Vue({
  el: '#app-2',
  data: {
      message: '页面加载于 ' + new Date().toLocaleString()
  }
});

var app3=new Vue({
    el:"#app-3",
    data:{
        seen:true
    }
});

//  for 循环
var app4=new Vue({
  el:"#app-4",
  data:{
    todos:[
     { text:'text1'} ,
     { text:'text2'} ,
     { text:'text3'}
    ]
  }
});

var app5 = new Vue({
  el:'#app-5',
  data:{
    message:'ababab'
  },
  methods: {
    reverseMessage:function(){
      // this.message="babab";
      this.message=this.message.split('').reverse().join('');
    }
  }
});

//  数据双向绑定
var app6=new Vue({
  el:'#app-6',
  data:{
    message:'default value'
  }
});

//  组件开始
Vue.component(
  'todo-item',{
    template:'<li>待办1</li>'
  }
)
var app7 = new Vue({
  el:'#app-7'
})
// 组件结束， 后面这个new的部分必须有，才会绑定

//我们应该能从父作用域将数据传到子组件才对。让我们来修改一下组件的定义，使之能够接受一个 prop：
Vue.component(
  'todo-item',{
    props: ['todo'],
    template:'<li>{{todo.text}}</li>'
  }
);

var app8 = new Vue({
  el:'#app-8',
  data:{
    groceryList:[
      { id: 0, text: '蔬菜' },
      { id: 1, text: '奶酪' },
      { id: 2, text: '随便其它什么人吃的东西' }
    ]
  }
});

// 注意的是只有当实例被创建时 data 中存在的属性才是响应式的。也就是说如果你添加一个新的属性，比如：
// vm.b = 'hi'
// 那么对 b 的改动将不会触发任何视图的更新。如果你知道你会在晚些时候需要一个属性，但是一开始它为空或不存在，那么你仅需要设置一些初始值。比如：
var data = {a:1};
var app9 = new Vue({
  el:'#app-9',
  data:data
});
data.b = 1
// 不会触发视图更新

//阻止viewmodel绑定
var obj = {
  foo:'bar'
};
// 阻止关键逻辑
Object.freeze(obj);

var app10 = new Vue({
  el:'#app-10',
  data: obj
});

//监控变更
var obj={a:1};

var app11 = new Vue({
  el:'#app-11',
  data:obj,
  //  比如 created 钩子可以用来在一个实例被创建之后执行代码：
  created () {
    console.log("a is " + this.a);
  }

});

console.log(app11.$data === data); //true
console.log(app11.$el = document.getElementById('#app-11'));

app11.$watch('a', function(newValue, oldValue){
  console.log("新值: "  + newValue + " 原值: " + oldValue); //新值: 66 原值: 1
});
obj.a=66;

// 三目运算, 必须打开vue环境mustache才会生效
var app11 = new Vue({
  el:'#app-11'
});

//computed
var app12 = new Vue({
  el:'#app-12',
  data:{
    message:'ababab'
  },
  //app12.reversedMessage 这种方式下，缓存，如果不改变message的值，不会再去执行reversedMessage
  computed:{
    reversedMessage(){
      console.log("computed --> reverMessage");
      return this.message.split("").reverse().join("");
    }
  },
  methods:{
    // 使用方法的形式， 每次都会执行, 这里名字不能使用同一个，否则识别其中一个
    reverseMessage: function(){
      console.log("methods --> reverseMessage");
      return this.message.split("").reverse().join("");
    }
  }
});

// 侦听器
var watchExampleVM = new Vue({
  el: '#watch-example',
  data: {
    question: '',
    answer: 'I cannot give you an answer until you ask a question!'
  },
  watch: {
    // 如果 `question` 发生改变，这个函数就会运行
    question: function (newQuestion, oldQuestion) {
      this.answer = 'Waiting for you to stop typing...'
      this.debouncedGetAnswer()
    }
  },
  created: function () {
    // `_.debounce` 是一个通过 Lodash 限制操作频率的函数。
    // 在这个例子中，我们希望限制访问 yesno.wtf/api 的频率
    // AJAX 请求直到用户输入完毕才会发出。想要了解更多关于
    // `_.debounce` 函数 (及其近亲 `_.throttle`) 的知识，
    // 请参考：https://lodash.com/docs#debounce
    this.debouncedGetAnswer = _.debounce(this.getAnswer, 500)
  },
  methods: {
    getAnswer: function () {
      // 不带？叫问题?
      if (this.question.indexOf('?') === -1) {
        this.answer = 'Questions usually contain a question mark. ;-)'
        return
      }
      this.answer = 'Thinking...'
      var vm = this
      axios.get('https://yesno.wtf/api')
        .then(function (response) {
          vm.answer = _.capitalize(response.data.answer)
        })
        .catch(function (error) {
          vm.answer = 'Error! Could not reach the API. ' + error
        })
    }
  }
});

var app13 = new Vue({
  el:'#app-13',
  data:{
    isActive: true,
  error: null
  },
  computed:{
    classObject(){
       return {
      active: this.isActive && !this.error,
      'text-danger': this.error && this.error.type === 'fatal'
    }
    }
  }
});

var app14=new Vue({
  el:'#app-14',
  data:{
    isActive:true,
  activeClass: 'active',
  errorClass: 'text-danger'
  }
});

var app15=new Vue({
  el:'#app-15',
  data:{
    activeColor:'red',
    fontSize:15,
    styleObject:{
      color:'yellow',
      fontSize:15,
    }

  }
})