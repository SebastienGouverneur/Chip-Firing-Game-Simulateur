package controler;

import model.ModelGraphTrans;
import view.ViewGraphTrans;

public class ControlerGraphTrans {
	private final ModelGraphTrans modelGraphTrans;
	private final ViewGraphTrans viewGraphTrans;

	ControlerGraphTrans(ViewGraphTrans viewGraphTrans,
			ModelGraphTrans modelGraphTrans) {
		this.modelGraphTrans = modelGraphTrans;
		this.viewGraphTrans = viewGraphTrans;

		modelGraphTrans.createViewGraph();
	}

	public void reset() {
		modelGraphTrans.reset();
	}
}