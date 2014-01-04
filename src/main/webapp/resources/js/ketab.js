
(function ($){

	//TODO: Use {{Mustache}} for templating http://blog.lynda.com/2012/12/27/introduction-to-javascript-templating-using-mustache-js/

	// Question Model and View

	Question = Backbone.Model.extend({
		defaults:{
			qId: '',
			qText: '',
			langId: ''
		},
		idAttribute: 'qId',
		urlRoot: 'controllers/question/'
	});

	QuestionView = Backbone.View.extend({
		model: Question,
		el: $('#container'),
		initialize: function(){
			this.listenTo(this.model, 'change', this.render);
			this.model.fetch();
		},
		render: function() {
			this.$el.append(
				'<div class="question">' +
				'<span class="qNum">' + this.model.get('qId') + '</span>' + 
				'<span class="qTitle">' + this.model.get('qText') + '</span>' +
				'</div>');
			console.log(q.toJSON());
			return this;
		}
	});

	q = new Question({'qId': 1});
	qView = new QuestionView({model: q});

	


	// Questions Collection and View

	Questions = Backbone.Collection.extend({
		model: Question,
		urlRoot: 'controllers/questions'

	});

	QuestionsView = Backbone.View.extend({
		model: Questions,
		el: 'container',
		tagName: 'ul',
		events: {
		}
	});
	
	
})(jQuery);

$('#container')