//
//  ViewController.h
//  SampleApp
//


#import <UIKit/UIKit.h>

@interface ViewController : UIViewController {
    
}
@property (strong, nonatomic) IBOutlet UIBarButtonItem *shareButtonItem;
- (IBAction)handleShareButtonItem:(id)sender;
@property (strong, nonatomic) IBOutlet UIButton *feature2Button;

@property (strong, nonatomic) IBOutlet UIButton *feature1Button;

@property (strong, nonatomic) IBOutlet UIButton *document1Button;

@property (strong, nonatomic) IBOutlet UIButton *asset1Button;

@property (strong, nonatomic) IBOutlet UIButton *image1Button;

@property (strong, nonatomic) IBOutlet UIButton *video1Button;

@property (strong, nonatomic) IBOutlet UIButton *music1Button;

@property (strong, nonatomic) IBOutlet UISegmentedControl *isFreeOrPaidSegControl;
- (IBAction)handleFreePaidSegControl:(id)sender;

@end
